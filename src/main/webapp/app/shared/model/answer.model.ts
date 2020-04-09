export interface IAnswer {
  id?: number;
  answer?: string;
  order?: number;
  isTheRightAnswer?: boolean;
  questionId?: number;
}

export class Answer implements IAnswer {
  constructor(
    public id?: number,
    public answer?: string,
    public order?: number,
    public isTheRightAnswer?: boolean,
    public questionId?: number
  ) {
    this.isTheRightAnswer = this.isTheRightAnswer || false;
  }
}
