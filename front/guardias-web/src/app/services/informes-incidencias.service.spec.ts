import { TestBed } from '@angular/core/testing';

import { InformesIncidenciasService } from './informes-incidencias.service';

describe('InformesIncidenciasService', () => {
  let service: InformesIncidenciasService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(InformesIncidenciasService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
