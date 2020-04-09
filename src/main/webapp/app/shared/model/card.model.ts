import { ValueType } from 'app/shared/model/enumerations/value-type.model';
import { SortingType } from 'app/shared/model/enumerations/sorting-type.model';

export interface ICard {
  id?: number;
  display?: string;
  valueToSort?: string;
  valueType?: ValueType;
  pictureContentType?: string;
  picture?: any;
  sortingType?: SortingType;
  order?: number;
  packId?: number;
  packName?: string;
}

export class Card implements ICard {
  constructor(
    public id?: number,
    public display?: string,
    public valueToSort?: string,
    public valueType?: ValueType,
    public pictureContentType?: string,
    public picture?: any,
    public sortingType?: SortingType,
    public order?: number,
    public packId?: number,
    public packName?: string
  ) {}
}
