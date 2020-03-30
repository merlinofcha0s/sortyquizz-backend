import { ICard } from 'app/shared/model/card.model';

export interface IProfile {
  id?: number;
  level?: string;
  cards?: ICard[];
  userLogin?: string;
  userId?: number;
}

export class Profile implements IProfile {
  constructor(public id?: number, public level?: string, public cards?: ICard[], public userLogin?: string, public userId?: number) {}
}
