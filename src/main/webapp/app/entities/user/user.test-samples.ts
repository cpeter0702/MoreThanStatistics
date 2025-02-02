import { IUser } from './user.model';

export const sampleWithRequiredData: IUser = {
  id: 10606,
  login: '4VZ',
};

export const sampleWithPartialData: IUser = {
  id: 16738,
  login: '8Ry',
};

export const sampleWithFullData: IUser = {
  id: 18365,
  login: '8@1oOc3',
};
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
