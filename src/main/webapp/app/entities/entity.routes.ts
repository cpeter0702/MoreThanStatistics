import { Routes } from '@angular/router';

const routes: Routes = [
  {
    path: 'authority',
    data: { pageTitle: 'moreThanStatisticsApp.adminAuthority.home.title' },
    loadChildren: () => import('./admin/authority/authority.routes'),
  },
  {
    path: 'mts-income',
    data: { pageTitle: 'moreThanStatisticsApp.mtsIncome.home.title' },
    loadChildren: () => import('./mts-income/mts-income.routes'),
  },
  {
    path: 'mts-expense',
    data: { pageTitle: 'moreThanStatisticsApp.mtsExpense.home.title' },
    loadChildren: () => import('./mts-expense/mts-expense.routes'),
  },
  {
    path: 'money-flow-view',
    data: { pageTitle: 'moreThanStatisticsApp.moneyFlowView.home.title' },
    loadChildren: () => import('./money-flow-view/money-flow-view.routes'),
  },
  {
    path: 'nayax-transactions',
    data: { pageTitle: 'moreThanStatisticsApp.nayaxTransactions.home.title' },
    loadChildren: () => import('./nayax-transactions/nayax-transactions.routes'),
  },
  /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
];

export default routes;
