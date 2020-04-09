import { PackState } from 'app/shared/model/enumerations/pack-state.model';

export interface IUserPack {
  id?: number;
  state?: PackState;
  lifeLeft?: number;
  nbQuestionsToSucceed?: number;
  timeAtQuizzStep?: number;
  timeAtSortingStep?: number;
  profileId?: number;
  packId?: number;
}

export class UserPack implements IUserPack {
  constructor(
    public id?: number,
    public state?: PackState,
    public lifeLeft?: number,
    public nbQuestionsToSucceed?: number,
    public timeAtQuizzStep?: number,
    public timeAtSortingStep?: number,
    public profileId?: number,
    public packId?: number
  ) {}
}
