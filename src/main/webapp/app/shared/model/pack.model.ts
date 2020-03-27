import { IQuestion } from 'app/shared/model/question.model';
import { ICard } from 'app/shared/model/card.model';

export interface IPack {
  id?: number;
  name?: string;
  category?: string;
  questions?: IQuestion[];
  cards?: ICard[];
}

export class Pack implements IPack {
  constructor(public id?: number, public name?: string, public category?: string, public questions?: IQuestion[], public cards?: ICard[]) {}
}
