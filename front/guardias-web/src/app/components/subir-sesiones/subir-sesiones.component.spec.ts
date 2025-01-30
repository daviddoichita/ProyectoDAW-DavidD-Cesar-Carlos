import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SubirSesionesComponent } from './subir-sesiones.component';

describe('SubirSesionesComponent', () => {
  let component: SubirSesionesComponent;
  let fixture: ComponentFixture<SubirSesionesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SubirSesionesComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(SubirSesionesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
