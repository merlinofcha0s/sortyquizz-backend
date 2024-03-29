import { IUserPack } from 'app/shared/model/user-pack.model';
import { IThemeScore } from 'app/shared/model/theme-score.model';

export interface IProfile {
  id?: number;
  level?: number;
  userPacks?: IUserPack[];
  themeScores?: IThemeScore[];
  userLogin?: string;
  userId?: number;
}

export class Profile implements IProfile {
  constructor(
    public id?: number,
    public level?: number,
    public userPacks?: IUserPack[],
    public themeScores?: IThemeScore[],
    public userLogin?: string,
    public userId?: number
  ) {}
}
