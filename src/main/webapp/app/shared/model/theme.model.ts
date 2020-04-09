import { IPack } from 'app/shared/model/pack.model';
import { IThemeScore } from 'app/shared/model/theme-score.model';

export interface ITheme {
  id?: number;
  name?: string;
  packs?: IPack[];
  themeScores?: IThemeScore[];
}

export class Theme implements ITheme {
  constructor(public id?: number, public name?: string, public packs?: IPack[], public themeScores?: IThemeScore[]) {}
}
