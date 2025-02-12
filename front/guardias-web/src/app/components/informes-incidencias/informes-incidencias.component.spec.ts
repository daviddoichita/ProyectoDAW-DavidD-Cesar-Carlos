import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InformesIncidenciasComponent } from './informes-incidencias.component';

describe('InformesIncidenciasComponent', () => {
  let component: InformesIncidenciasComponent;
  let fixture: ComponentFixture<InformesIncidenciasComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [InformesIncidenciasComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(InformesIncidenciasComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
