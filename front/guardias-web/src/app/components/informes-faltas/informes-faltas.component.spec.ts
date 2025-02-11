import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InformesFaltasComponent } from './informes-faltas.component';

describe('InformesFaltasComponent', () => {
  let component: InformesFaltasComponent;
  let fixture: ComponentFixture<InformesFaltasComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [InformesFaltasComponent]
    })
      .compileComponents();

    fixture = TestBed.createComponent(InformesFaltasComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
