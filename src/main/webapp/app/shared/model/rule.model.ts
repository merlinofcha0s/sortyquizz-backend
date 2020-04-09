export interface IRule {
  id?: number;
  nbMaxQuestions?: number;
  timePerQuestion?: number;
  timeForSorting?: number;
  nbMinCardToWin?: number;
}

export class Rule implements IRule {
  constructor(
    public id?: number,
    public nbMaxQuestions?: number,
    public timePerQuestion?: number,
    public timeForSorting?: number,
    public nbMinCardToWin?: number
  ) {}
}
