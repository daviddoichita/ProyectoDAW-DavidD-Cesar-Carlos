import { ComponentFixture, TestBed } from '@angular/core/testing';
import { NuevaFaltaComponent } from './nueva-falta.component';

describe('NuevaFaltaComponent', () => {
  let component: NuevaFaltaComponent;
  let fixture: ComponentFixture<NuevaFaltaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [NuevaFaltaComponent]
    })
      .compileComponents();

    fixture = TestBed.createComponent(NuevaFaltaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});