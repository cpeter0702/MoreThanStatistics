import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { NayaxTransactionsDetailComponent } from './nayax-transactions-detail.component';

describe('NayaxTransactions Management Detail Component', () => {
  let comp: NayaxTransactionsDetailComponent;
  let fixture: ComponentFixture<NayaxTransactionsDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [NayaxTransactionsDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: NayaxTransactionsDetailComponent,
              resolve: { nayaxTransactions: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(NayaxTransactionsDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(NayaxTransactionsDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load nayaxTransactions on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', NayaxTransactionsDetailComponent);

      // THEN
      expect(instance.nayaxTransactions).toEqual(expect.objectContaining({ id: 123 }));
    });
  });

  describe('PreviousState', () => {
    it('Should navigate to previous state', () => {
      jest.spyOn(window.history, 'back');
      comp.previousState();
      expect(window.history.back).toHaveBeenCalled();
    });
  });
});
