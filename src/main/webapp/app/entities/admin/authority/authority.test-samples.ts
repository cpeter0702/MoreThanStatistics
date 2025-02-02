import { IAuthority, NewAuthority } from './authority.model';

export const sampleWithRequiredData: IAuthority = {
  name: '8c2a195c-35bf-4b8a-a4d7-b9792418d86e',
};

export const sampleWithPartialData: IAuthority = {
  name: '85d87397-149d-4fc4-a01b-cabddaf97480',
};

export const sampleWithFullData: IAuthority = {
  name: '99605026-1a10-4514-914e-68dbddff2d7f',
};

export const sampleWithNewData: NewAuthority = {
  name: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
