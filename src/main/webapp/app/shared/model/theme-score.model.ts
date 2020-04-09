export interface IThemeScore {
  id?: number;
  xp?: number;
  level?: number;
  profileId?: number;
  themeId?: number;
}

export class ThemeScore implements IThemeScore {
  constructor(public id?: number, public xp?: number, public level?: number, public profileId?: number, public themeId?: number) {}
}
