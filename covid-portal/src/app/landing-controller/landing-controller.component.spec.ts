import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LandingControllerComponent } from './landing-controller.component';

describe('LandingControllerComponent', () => {
  let component: LandingControllerComponent;
  let fixture: ComponentFixture<LandingControllerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LandingControllerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LandingControllerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
