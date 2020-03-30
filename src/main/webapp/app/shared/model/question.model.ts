import { IAnswer } from 'app/shared/model/answer.model';

export interface IQuestion {
  id?: number;
  question?: string;
  level?: number;
  answers?: IAnswer[];
  packId?: number;
  packName?: string;
}

export class Question implements IQuestion {
  constructor(
    public id?: number,
    public question?: string,
    public level?: number,
    public answers?: IAnswer[],
    public packId?: number,
    public packName?: string
  ) {}
}
