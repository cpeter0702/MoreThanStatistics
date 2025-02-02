import dayjs from 'dayjs/esm';

import { IMtsIncome, NewMtsIncome } from './mts-income.model';

export const sampleWithRequiredData: IMtsIncome = {
  id: 330,
  incomeDate: dayjs('2025-02-02T02:36'),
  incomeAmount: 20900.17,
};

export const sampleWithPartialData: IMtsIncome = {
  id: 11778,
  incomeDate: dayjs('2025-02-01T23:49'),
  incomeAmount: 18196.18,
  incomeRemark: 'along',
  modifier: 'spruce supposing',
  modifyDatetime: dayjs('2025-02-02T00:38'),
  createDatetime: dayjs('2025-02-02T06:14'),
};

export const sampleWithFullData: IMtsIncome = {
  id: 3727,
  incomeDate: dayjs('2025-02-01T22:12'),
  incomeAmount: 27211.66,
  incomeType: 'attractive oof',
  incomeTypeDetail: 'yum since supersize',
  incomePayer: 'towards physically',
  incomeReceiver: 'although variable',
  incomeRemark: 'formula',
  isActive: true,
  modifier: 'carelessly',
  modifyDatetime: dayjs('2025-02-01T17:11'),
  creator: 'cleverly',
  createDatetime: dayjs('2025-02-01T07:44'),
};

export const sampleWithNewData: NewMtsIncome = {
  incomeDate: dayjs('2025-02-02T02:45'),
  incomeAmount: 10700.61,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
