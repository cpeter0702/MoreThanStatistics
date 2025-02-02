import { IAuthority, NewAuthority } from './authority.model';

export const sampleWithRequiredData: IAuthority = {
  name: 'd517fc3f-3891-4ddd-adda-cacb55f8617d',
};

export const sampleWithPartialData: IAuthority = {
  name: 'e91b93f0-1075-4bb6-bdbd-c246dc9534a2',
};

export const sampleWithFullData: IAuthority = {
  name: 'a3ff3cea-8e16-4aba-9a53-b11c8a498584',
};

export const sampleWithNewData: NewAuthority = {
  name: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
