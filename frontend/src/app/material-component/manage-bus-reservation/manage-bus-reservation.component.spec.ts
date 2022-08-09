import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ManageBusReservationComponent } from './manage-bus-reservation.component';

describe('ManageBusReservationComponent', () => {
  let component: ManageBusReservationComponent;
  let fixture: ComponentFixture<ManageBusReservationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ManageBusReservationComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ManageBusReservationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
