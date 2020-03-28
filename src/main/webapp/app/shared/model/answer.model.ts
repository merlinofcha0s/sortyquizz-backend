export interface IAnswer {
  id?: number;
  answer?: string;
  order?: number;
  questionId?: number;
}

export class Answer implements IAnswer {
  constructor(public id?: number, public answer?: string, public order?: number, public questionId?: number) {}
}
