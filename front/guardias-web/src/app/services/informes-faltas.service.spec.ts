import { TestBed } from '@angular/core/testing';

import { InformesFaltasService } from './informes-faltas.service';

describe('InformesFaltasService', () => {
  let service: InformesFaltasService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(InformesFaltasService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
