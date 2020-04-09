import { IAnswer } from 'app/shared/model/answer.model';
import { QuestionType } from 'app/shared/model/enumerations/question-type.model';

export interface IQuestion {
  id?: number;
  question?: string;
  type?: QuestionType;
  answers?: IAnswer[];
  packId?: number;
  packName?: string;
}

export class Question implements IQuestion {
  constructor(
    public id?: number,
    public question?: string,
    public type?: QuestionType,
    public answers?: IAnswer[],
    public packId?: number,
    public packName?: string
  ) {}
}
