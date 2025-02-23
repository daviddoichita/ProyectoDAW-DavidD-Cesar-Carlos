import { TestBed } from '@angular/core/testing';

import { FaltaService } from './falta.service';

describe('FaltaService', () => {
  let service: FaltaService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(FaltaService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
