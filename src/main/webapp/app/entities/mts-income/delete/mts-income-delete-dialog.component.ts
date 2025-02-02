import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IMtsIncome } from '../mts-income.model';
import { MtsIncomeService } from '../service/mts-income.service';

@Component({
  standalone: true,
  templateUrl: './mts-income-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class MtsIncomeDeleteDialogComponent {
  mtsIncome?: IMtsIncome;

  protected mtsIncomeService = inject(MtsIncomeService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.mtsIncomeService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
