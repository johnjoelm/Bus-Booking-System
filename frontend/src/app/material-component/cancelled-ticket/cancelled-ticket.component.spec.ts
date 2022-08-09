import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CancelledTicketComponent } from './cancelled-ticket.component';

describe('CancelledTicketComponent', () => {
  let component: CancelledTicketComponent;
  let fixture: ComponentFixture<CancelledTicketComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CancelledTicketComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CancelledTicketComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
