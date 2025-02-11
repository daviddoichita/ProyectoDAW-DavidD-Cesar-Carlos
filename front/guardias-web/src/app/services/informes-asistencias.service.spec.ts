import { TestBed } from '@angular/core/testing';

import { InformesAsistenciasService } from './informes-asistencias.service';

describe('InformesAsistenciasService', () => {
  let service: InformesAsistenciasService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(InformesAsistenciasService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
