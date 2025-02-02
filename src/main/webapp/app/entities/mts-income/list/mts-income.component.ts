import { Component, NgZone, inject, OnInit } from '@angular/core';
import { ActivatedRoute, Data, ParamMap, Router, RouterModule } from '@angular/router';
import { combineLatest, filter, Observable, Subscription, tap } from 'rxjs';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { sortStateSignal, SortDirective, SortByDirective, type SortState, SortService } from 'app/shared/sort';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { FormsModule } from '@angular/forms';
import { SORT, ITEM_DELETED_EVENT, DEFAULT_SORT_DATA } from 'app/config/navigation.constants';
import { IMtsIncome } from '../mts-income.model';
import { EntityArrayResponseType, MtsIncomeService } from '../service/mts-income.service';
import { MtsIncomeDeleteDialogComponent } from '../delete/mts-income-delete-dialog.component';
import { AgCharts } from 'ag-charts-angular';
import { AgChartOptions } from 'ag-charts-community';

@Component({
  standalone: true,
  selector: 'jhi-mts-income',
  templateUrl: './mts-income.component.html',
  imports: [
    RouterModule,
    FormsModule,
    SharedModule,
    SortDirective,
    SortByDirective,
    DurationPipe,
    FormatMediumDatetimePipe,
    FormatMediumDatePipe,
  ],
})
export class MtsIncomeComponent implements OnInit {
  subscription: Subscription | null = null;
  mtsIncomes?: IMtsIncome[];
  isLoading = false;

  // ===== table =====================================================================================
  columnDefs = [
    { headerName: 'Id', field: 'id', filter: true, sortable: true, checkboxSelection: true },
    { headerName: 'Make', field: 'make', filter: true, sortable: true, checkboxSelection: true },
    { headerName: 'Model', field: 'model', filter: true, sortable: true, checkboxSelection: true },
    { headerName: 'Price', field: 'price', filter: true, sortable: true, checkboxSelection: true },
    {
      headerName: 'Operation',
      field: 'action',
      cellRenderer: (params: any) => {
        return `<button class="custom-btn" onclick="alert('ID: ${params.data.id}')">查看</button>`;
      },
    },
  ];
  rowData = [
    { id: 1, make: 'Toyota', model: 'Camry', price: 35000 },
    { id: 2, make: 'Ford', model: 'Focus', price: 29000 },
    { id: 3, make: 'Porsche', model: 'Boxster', price: 72000 },
  ];
  // ===== table =====================================================================================
  sortState = sortStateSignal({});

  public router = inject(Router);
  protected mtsIncomeService = inject(MtsIncomeService);
  protected activatedRoute = inject(ActivatedRoute);
  protected sortService = inject(SortService);
  protected modalService = inject(NgbModal);
  protected ngZone = inject(NgZone);

  trackId = (_index: number, item: IMtsIncome): number => this.mtsIncomeService.getMtsIncomeIdentifier(item);

  ngOnInit(): void {
    this.subscription = combineLatest([this.activatedRoute.queryParamMap, this.activatedRoute.data])
      .pipe(
        tap(([params, data]) => this.fillComponentAttributeFromRoute(params, data)),
        tap(() => {
          if (!this.mtsIncomes || this.mtsIncomes.length === 0) {
            this.load();
          }
        }),
      )
      .subscribe();
  }

  delete(mtsIncome: IMtsIncome): void {
    const modalRef = this.modalService.open(MtsIncomeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.mtsIncome = mtsIncome;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed
      .pipe(
        filter(reason => reason === ITEM_DELETED_EVENT),
        tap(() => this.load()),
      )
      .subscribe();
  }

  load(): void {
    this.queryBackend().subscribe({
      next: (res: EntityArrayResponseType) => {
        this.onResponseSuccess(res);
      },
    });
  }

  navigateToWithComponentValues(event: SortState): void {
    this.handleNavigation(event);
  }

  protected fillComponentAttributeFromRoute(params: ParamMap, data: Data): void {
    this.sortState.set(this.sortService.parseSortParam(params.get(SORT) ?? data[DEFAULT_SORT_DATA]));
  }

  protected onResponseSuccess(response: EntityArrayResponseType): void {
    const dataFromBody = this.fillComponentAttributesFromResponseBody(response.body);
    this.mtsIncomes = this.refineData(dataFromBody);
  }

  protected refineData(data: IMtsIncome[]): IMtsIncome[] {
    const { predicate, order } = this.sortState();
    return predicate && order ? data.sort(this.sortService.startSort({ predicate, order })) : data;
  }

  protected fillComponentAttributesFromResponseBody(data: IMtsIncome[] | null): IMtsIncome[] {
    return data ?? [];
  }

  protected queryBackend(): Observable<EntityArrayResponseType> {
    this.isLoading = true;
    const queryObject: any = {
      sort: this.sortService.buildSortParam(this.sortState()),
    };
    return this.mtsIncomeService.query(queryObject).pipe(tap(() => (this.isLoading = false)));
  }

  protected handleNavigation(sortState: SortState): void {
    const queryParamsObj = {
      sort: this.sortService.buildSortParam(sortState),
    };

    this.ngZone.run(() => {
      this.router.navigate(['./'], {
        relativeTo: this.activatedRoute,
        queryParams: queryParamsObj,
      });
    });
  }
}
