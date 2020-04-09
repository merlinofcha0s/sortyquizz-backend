import { XPType } from 'app/shared/model/enumerations/xp-type.model';

export interface IReferenceXP {
  id?: number;
  level?: number;
  minXp?: number;
  maxXp?: number;
  xpType?: XPType;
}

export class ReferenceXP implements IReferenceXP {
  constructor(public id?: number, public level?: number, public minXp?: number, public maxXp?: number, public xpType?: XPType) {}
}
