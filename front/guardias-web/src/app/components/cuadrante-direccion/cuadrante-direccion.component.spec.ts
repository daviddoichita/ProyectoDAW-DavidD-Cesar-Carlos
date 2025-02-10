import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CuadranteDireccionComponent } from './cuadrante-direccion.component';

describe('CuadranteDireccionComponent', () => {
  let component: CuadranteDireccionComponent;
  let fixture: ComponentFixture<CuadranteDireccionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CuadranteDireccionComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(CuadranteDireccionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
