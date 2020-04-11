package fr.sortyquizz;

import fr.sortyquizz.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DBClearer {

    @Autowired
    private ThemeRepository themeRepository;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private PackRepository packRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ThemeScoreRepository themeScoreRepository;

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;


    public void clearAllTables(){
        themeScoreRepository.deleteAll();
        profileRepository.deleteAll();
        userRepository.deleteAll();
        cardRepository.deleteAll();
        answerRepository.deleteAll();
        questionRepository.deleteAll();
        packRepository.deleteAll();
        themeRepository.deleteAll();
    }


}
