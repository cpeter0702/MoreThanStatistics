import dayjs from 'dayjs/esm';

import { IMtsExpense, NewMtsExpense } from './mts-expense.model';

export const sampleWithRequiredData: IMtsExpense = {
  id: 17199,
  expenseDate: dayjs('2025-02-01T23:42'),
  expenseAmount: 21136.45,
};

export const sampleWithPartialData: IMtsExpense = {
  id: 20752,
  expenseDate: dayjs('2025-02-02T05:30'),
  expenseAmount: 5808.75,
  expenseType: 'indeed',
  isActive: true,
  modifier: 'tame',
  modifyDatetime: dayjs('2025-02-02T01:31'),
  creator: 'specific amid',
  createDatetime: dayjs('2025-02-02T00:22'),
};

export const sampleWithFullData: IMtsExpense = {
  id: 28216,
  expenseDate: dayjs('2025-02-02T03:39'),
  expenseAmount: 29072.31,
  expenseType: 'underneath into',
  expenseTypeDetail: 'yearly inasmuch',
  expensePayer: 'next grubby cargo',
  expenseReceiver: 'soggy slim',
  expenseRemark: 'intrepid',
  expenseReceipt: '../fake-data/blob/hipster.png',
  expenseReceiptContentType: 'unknown',
  isActive: true,
  modifier: 'plan from',
  modifyDatetime: dayjs('2025-02-02T02:49'),
  creator: 'termination',
  createDatetime: dayjs('2025-02-02T03:12'),
};

export const sampleWithNewData: NewMtsExpense = {
  expenseDate: dayjs('2025-02-01T15:48'),
  expenseAmount: 26729.02,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
