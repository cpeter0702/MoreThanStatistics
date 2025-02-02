import dayjs from 'dayjs/esm';

import { INayaxTransactions, NewNayaxTransactions } from './nayax-transactions.model';

export const sampleWithRequiredData: INayaxTransactions = {
  id: 624,
  transactionID: 'cavil',
  currency: 'amongst scary testing',
  machineName: 'whoa',
  productSelectionInfo: 'ugh sweetly',
  cardNumber: 'topsail vet',
  authrizationRRN: 'yum astride versus',
};

export const sampleWithPartialData: INayaxTransactions = {
  id: 26207,
  siteID: 20547,
  transactionID: 'clumsy',
  paymentMethodID: 25660,
  currency: 'frantically',
  machineName: 'deep',
  productSelectionInfo: 'wherever worthy',
  cardNumber: 'quaintly toward',
  authrizationRRN: 'loving',
  cardType: 'full ick mandolin',
  transactionStatusID: 14868,
  transactionTypeID: 7464,
  billingProvider: 'hallway off proof-reader',
  refundRequestDate: dayjs('2025-02-01T23:36'),
  refundReason: 'forenenst crowded whenever',
};

export const sampleWithFullData: INayaxTransactions = {
  id: 27471,
  siteID: 24756,
  transactionID: 'questioningly appropriate',
  paymentMethodID: 12047,
  currency: 'now pfft',
  machineName: 'impressionable provided',
  authorizationValue: 8506,
  campaignID: 'properly remote yahoo',
  settlementValue: 32567.26,
  productSelectionInfo: 'youthfully impair',
  cardNumber: 'huzzah configure',
  authrizationRRN: 'bluff',
  machineAuthorizationTime: dayjs('2025-02-02T05:29'),
  machineSettlementTime: dayjs('2025-02-02T05:00'),
  creditCardType: 'transform left ah',
  cardType: 'rate',
  paymentMethod: 'nationalize',
  transactionStatusID: 31626,
  transactionTypeID: 10567,
  billingProvider: 'poorly woot only',
  prepaidCardHolderName: 'barring',
  refundRequestBy: 'loud',
  refundRequestDate: dayjs('2025-02-01T18:40'),
  refundReason: 'velvety',
};

export const sampleWithNewData: NewNayaxTransactions = {
  transactionID: 'ouch that midst',
  currency: 'wedge whenever strictly',
  machineName: 'calm towards',
  productSelectionInfo: 'whether including',
  cardNumber: 'caboose',
  authrizationRRN: 'whittle',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
