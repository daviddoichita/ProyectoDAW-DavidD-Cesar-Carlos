import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InformesAsistenciasComponent } from './informes-asistencias.component';

describe('InformesAsistenciasComponent', () => {
  let component: InformesAsistenciasComponent;
  let fixture: ComponentFixture<InformesAsistenciasComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [InformesAsistenciasComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(InformesAsistenciasComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
