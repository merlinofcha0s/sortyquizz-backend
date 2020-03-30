import { IProfile } from 'app/shared/model/profile.model';

export interface ICard {
  id?: number;
  name?: string;
  level?: number;
  pictureContentType?: string;
  picture?: any;
  order?: number;
  profiles?: IProfile[];
  packId?: number;
  packName?: string;
}

export class Card implements ICard {
  constructor(
    public id?: number,
    public name?: string,
    public level?: number,
    public pictureContentType?: string,
    public picture?: any,
    public order?: number,
    public profiles?: IProfile[],
    public packId?: number,
    public packName?: string
  ) {}
}
