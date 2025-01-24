import { TestBed } from '@angular/core/testing';

import { CuadranteService } from './cuadrante.service';

describe('CuadranteService', () => {
  let service: CuadranteService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CuadranteService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
