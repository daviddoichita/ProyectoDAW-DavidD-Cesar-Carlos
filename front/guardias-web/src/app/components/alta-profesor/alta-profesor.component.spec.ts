import { ComponentFixture, TestBed } from '@angular/core/testing';
import { AltaProfesorComponent } from './alta-profesor.component';

describe('AltaProfesorComponent', () => {
  let component: AltaProfesorComponent;
  let fixture: ComponentFixture<AltaProfesorComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AltaProfesorComponent]
    })
      .compileComponents();

    fixture = TestBed.createComponent(AltaProfesorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
