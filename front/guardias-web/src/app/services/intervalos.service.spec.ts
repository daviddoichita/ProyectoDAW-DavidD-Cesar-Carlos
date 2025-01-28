import { TestBed } from '@angular/core/testing';

import { IntervalosService } from './intervalos.service';

describe('IntervalosService', () => {
  let service: IntervalosService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(IntervalosService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
