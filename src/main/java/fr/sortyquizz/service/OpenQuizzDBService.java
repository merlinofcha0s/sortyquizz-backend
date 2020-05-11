package fr.sortyquizz.service;

import fr.sortyquizz.domain.*;
import fr.sortyquizz.domain.enumeration.PackType;
import fr.sortyquizz.domain.enumeration.QuestionType;
import fr.sortyquizz.domain.enumeration.SortingType;
import fr.sortyquizz.domain.enumeration.ValueType;
import fr.sortyquizz.service.dto.CardDTO;
import fr.sortyquizz.service.dto.imports.openquizzdb.LevelOpenDB;
import fr.sortyquizz.service.dto.imports.openquizzdb.LocalizedQuizzDB;
import fr.sortyquizz.service.dto.imports.openquizzdb.QuestionOpenDB;
import fr.sortyquizz.service.dto.imports.openquizzdb.ThemeQuizzDBDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OpenQuizzDBService {

    private final PackService packService;
    private final QuestionService questionService;
    private final AnswerService answerService;
    private final RuleService ruleService;
    private final ThemeService themeService;
    private final CardService cardService;

    public OpenQuizzDBService(PackService packService,
                              QuestionService questionService,
                              AnswerService answerService,
                              RuleService ruleService,
                              ThemeService themeService,
                              CardService cardService) {
        this.packService = packService;
        this.questionService = questionService;
        this.answerService = answerService;
        this.ruleService = ruleService;
        this.themeService = themeService;
        this.cardService = cardService;
    }

    @Transactional
    public Boolean importQuizz(ThemeQuizzDBDTO themeQuizzDBDTO) {
        Theme newTheme = new Theme().name(themeQuizzDBDTO.getTheme());
        themeService.save(newTheme);

        if (!themeQuizzDBDTO.getQuizzDBDTO().getDebutant().isEmpty()) {
            importPack(newTheme, themeQuizzDBDTO.getQuizzDBDTO().getDebutant(), LevelOpenDB.BEGINNER);
        }

        if (!themeQuizzDBDTO.getQuizzDBDTO().getConfirme().isEmpty()) {
            importPack(newTheme, themeQuizzDBDTO.getQuizzDBDTO().getConfirme(), LevelOpenDB.INTERMEDIATE);
        }

        if (!themeQuizzDBDTO.getQuizzDBDTO().getExpert().isEmpty()) {
            importPack(newTheme, themeQuizzDBDTO.getQuizzDBDTO().getExpert(), LevelOpenDB.EXPERT);
        }

//        importByLocalizedPack(themeQuizzDBDTO.getQuizzDBDTO().getLocalizedQuizzDBEN(), newTheme);
        importByLocalizedPack(themeQuizzDBDTO.getQuizzDBDTO().getLocalizedQuizzDBFR(), newTheme);

        return true;
    }

    private void importByLocalizedPack(List<LocalizedQuizzDB> localizedQuizzDB, Theme newTheme) {
        if (localizedQuizzDB != null) {
            LocalizedQuizzDB localizedQuizzDB1 = localizedQuizzDB.stream().findFirst().get();
            if (!localizedQuizzDB1.getDebutant().isEmpty()) {
                importPack(newTheme, localizedQuizzDB1.getDebutant(), LevelOpenDB.BEGINNER);
            }

            if (!localizedQuizzDB1.getConfirme().isEmpty()) {
                importPack(newTheme, localizedQuizzDB1.getConfirme(), LevelOpenDB.INTERMEDIATE);
            }

            if (!localizedQuizzDB1.getExpert().isEmpty()) {
                importPack(newTheme, localizedQuizzDB1.getExpert(), LevelOpenDB.EXPERT);
            }
        }
    }

    private void importPack(Theme newTheme, List<QuestionOpenDB> questionOpenDBS, LevelOpenDB levelOpenDB) {
        Pack newPack = new Pack().theme(newTheme)
            .level(LevelOpenDB.BEGINNER.ordinal() + 1)
            .life(10)
            .name(newTheme.getName() + " " + levelOpenDB.name())
            .type(PackType.FREE);
        packService.save(newPack);

        questionOpenDBS.stream()
            .map((QuestionOpenDB questionOpenDB) -> mappingOpenDBQuestionToQuestion(questionOpenDB, newPack))
            .forEach(newPack::addQuestion);

        packService.save(newPack);

        Rule rule = new Rule()
            .nbMaxQuestions((int) Math.round(questionOpenDBS.size() * 0.80))
            .nbMinCardToWin(5)
            .timeForSorting(30)
            .timePerQuestion(15);

        Rule ruleSaved = ruleService.save(rule);
        newPack.setRule(ruleSaved);

        List<CardDTO> cardsToAdd = cardService.getByPackId(1L);
        for (int i = 0; i < cardsToAdd.size(); i++) {
            CardDTO cardDTO = cardsToAdd.get(i);
            cardDTO.setId(null);
            cardDTO.setPackId(newPack.getId());
            cardDTO.setOrder(i + 1);
            cardDTO.setSortingType(SortingType.NATURAL);
            cardDTO.setValueType(ValueType.STRING);
            cardDTO.setDisplay((i + 1) + "-" + cardDTO.getDisplay());
            Card card = cardService.saveAndReturnEntity(cardDTO);
            newPack.addCard(card);
        }
        packService.save(newPack);
    }


    private Question mappingOpenDBQuestionToQuestion(QuestionOpenDB questionOpenDB, Pack pack) {
        Question newQuestion = new Question()
            .question(questionOpenDB.getQuestion())
            .type(QuestionType.SIMPLE)
            .pack(pack);

        Set<Answer> answers = questionOpenDB.getAnswers()
            .stream()
            .map(answer -> mappingOpenDBAnswerToAnswer(answer, newQuestion, questionOpenDB.getReponse()))
            .collect(Collectors.toSet());
        newQuestion.setAnswers(answers);

        return questionService.save(newQuestion);
    }

    private Answer mappingOpenDBAnswerToAnswer(String answer, Question question, String reponse) {
        Answer newAnswer = new Answer().answer(answer).question(question);
        newAnswer.setIsTheRightAnswer(reponse.equalsIgnoreCase(answer));
        return answerService.save(newAnswer);
    }
}
