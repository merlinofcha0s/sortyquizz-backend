import { IAnswer } from 'app/shared/model/answer.model';
import { IPack } from 'app/shared/model/pack.model';

export interface IQuestion {
  id?: number;
  question?: string;
  level?: number;
  answers?: IAnswer[];
  pack?: IPack;
}

export class Question implements IQuestion {
  constructor(public id?: number, public question?: string, public level?: number, public answers?: IAnswer[], public pack?: IPack) {}
}
