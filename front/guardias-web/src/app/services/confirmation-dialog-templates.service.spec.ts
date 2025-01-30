import { TestBed } from '@angular/core/testing';

import { ConfirmationDialogTemplatesService } from './confirmation-dialog-templates.service';

describe('ConfirmationDialogTemplatesService', () => {
  let service: ConfirmationDialogTemplatesService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ConfirmationDialogTemplatesService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
