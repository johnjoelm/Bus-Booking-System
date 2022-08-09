import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ManageBusComponent } from './manage-bus.component';

describe('ManageBusComponent', () => {
  let component: ManageBusComponent;
  let fixture: ComponentFixture<ManageBusComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ManageBusComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ManageBusComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
