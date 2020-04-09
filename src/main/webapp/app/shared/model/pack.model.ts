import { IQuestion } from 'app/shared/model/question.model';
import { ICard } from 'app/shared/model/card.model';
import { IUserPack } from 'app/shared/model/user-pack.model';
import { PackType } from 'app/shared/model/enumerations/pack-type.model';

export interface IPack {
  id?: number;
  name?: string;
  level?: number;
  type?: PackType;
  life?: number;
  ruleId?: number;
  questions?: IQuestion[];
  cards?: ICard[];
  userPacks?: IUserPack[];
  themeId?: number;
}

export class Pack implements IPack {
  constructor(
    public id?: number,
    public name?: string,
    public level?: number,
    public type?: PackType,
    public life?: number,
    public ruleId?: number,
    public questions?: IQuestion[],
    public cards?: ICard[],
    public userPacks?: IUserPack[],
    public themeId?: number
  ) {}
}
