import dayjs from 'dayjs/esm';

import { IMoneyFlowView, NewMoneyFlowView } from './money-flow-view.model';

export const sampleWithRequiredData: IMoneyFlowView = {
  id: 8419,
  source: 'bleakly',
  businessId: '74139723-a1b0-4e09-aded-9e525014e9da',
  businessDate: dayjs('2025-02-01T16:32'),
  businessAmt: 20111.38,
};

export const sampleWithPartialData: IMoneyFlowView = {
  id: 7620,
  source: 'instead before fusarium',
  businessId: '56aeaf19-7912-4b80-b76f-d4468da01386',
  businessDate: dayjs('2025-02-01T17:22'),
  businessAmt: 19611.13,
  businessTypeDetail: 'however',
  receiver: 'enormous midst er',
  isActive: true,
};

export const sampleWithFullData: IMoneyFlowView = {
  id: 24647,
  source: 'earnest engineer bicker',
  businessId: 'e6625b74-f44a-4574-b5e2-00d7ead53f8a',
  businessDate: dayjs('2025-02-01T11:08'),
  businessAmt: 11854.37,
  businessType: 'unused discourse at',
  businessTypeDetail: 'gadzooks spread',
  payer: 'gradient gliding amalgamate',
  receiver: 'randomize abaft',
  remark: 'mid unbearably acceptance',
  isActive: false,
};

export const sampleWithNewData: NewMoneyFlowView = {
  source: 'junket bag likable',
  businessId: '05e3ea48-295f-4ae9-b369-db8570a0745d',
  businessDate: dayjs('2025-02-01T12:54'),
  businessAmt: 28343.61,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
