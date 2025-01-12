import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CuadranteProfesorComponent } from './cuadrante-profesor.component';

describe('CuadranteProfesorComponent', () => {
  let component: CuadranteProfesorComponent;
  let fixture: ComponentFixture<CuadranteProfesorComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CuadranteProfesorComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(CuadranteProfesorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
