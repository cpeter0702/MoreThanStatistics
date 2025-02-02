import { IUser } from './user.model';

export const sampleWithRequiredData: IUser = {
  id: 8656,
  login: 'DJ@gYty\\kjkV-n',
};

export const sampleWithPartialData: IUser = {
  id: 14803,
  login: 'Wg',
};

export const sampleWithFullData: IUser = {
  id: 1879,
  login: 'oV',
};
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
