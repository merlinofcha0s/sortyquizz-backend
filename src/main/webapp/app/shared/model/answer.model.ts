import { IQuestion } from 'app/shared/model/question.model';

export interface IAnswer {
  id?: number;
  answer?: string;
  order?: number;
  question?: IQuestion;
}

export class Answer implements IAnswer {
  constructor(public id?: number, public answer?: string, public order?: number, public question?: IQuestion) {}
}
