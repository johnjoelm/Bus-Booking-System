import { Component, OnInit, EventEmitter, Inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { BusService } from 'src/app/services/bus.service';
import { SnackbarService } from 'src/app/services/snackbar.service';
import { GlobalConstants } from 'src/app/shared/global-constants';

@Component({
  selector: 'app-bus',
  templateUrl: './bus.component.html',
  styleUrls: ['./bus.component.scss']
})
export class BusComponent implements OnInit {
  onAddBus = new EventEmitter();
  onEditBus = new EventEmitter();
  busForm: any = FormGroup;
  dialogAction: any = "Add";
  action: any = "Add";
  responseMessage: any;
  categorys: any = [];
  days: any = ["Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"];
  times: any = ["00:00", "00:30", "01:00",
    "01:30",
    "02:00",
    "02:30",
    "03:00",
    "03:30",
    "04:00", "04:30", "05:00",
    "05:30", "06:00", "06:30", "07:00", "07:30",
    "08:00", "08:30", "09:00", "09:30", "10:00",
    "10:30", "11:00", "11:30", "12:00", "12:30",
    "13:00",
    "13:30",
    "14:00",
    "14:30",
    "15:00",
    "15:30",
    "16:00",
    "16:30",
    "17:00",
    "17:30",
    "18:00",
    "18:30",
    "19:00",
    "19:30",
    "20:00",
    "20:30",
    "21:00", "21:30", "22:00", "22:30", "23:00", "23:30"
  ];

  constructor(@Inject(MAT_DIALOG_DATA) public dialogData: any,
    private formBuilder: FormBuilder,
    public dialogRef: MatDialogRef<BusComponent>,
    private snackbarService: SnackbarService,
    private busService:BusService) { }

  ngOnInit(): void {
    this.busForm = this.formBuilder.group({
      busName: [null, [Validators.required]],
      day: [null, Validators.required],
      from: [null, Validators.required],
      to: [null, Validators.required],
      departTime: [null, Validators.required],
      arivalTime: [null, Validators.required],
      chargePerSeat: [null, Validators.required]
    })

    if (this.dialogData.action === 'Edit') {
      this.dialogAction = "Edit";
      this.action = "Update";
      this.busForm.patchValue(this.dialogData.data);
    }
  }

  handleSubmit() {
    if (this.dialogAction === 'Edit') {
      this.edit();
    }
    else {
      this.add();
    }
  }

  add() {
    var formData = this.busForm.value;
    var data = {
      busName: formData.busName,
      day: formData.day,
      from: formData.from,
      to: formData.to,
      departTime:formData.departTime,
      arivalTime:formData.arivalTime,
      chargePerSeat:formData.chargePerSeat
    }
    this.busService.addBus(data).subscribe((response: any) => {
      this.dialogRef.close();
      this.onAddBus.emit();
      this.responseMessage = response.message;
      this.snackbarService.openSnackBar(this.responseMessage, "success");
    }, (error: any) => {
      if (error.error?.message) {
        this.responseMessage = error.error?.message;
      }
      else {
        this.responseMessage = GlobalConstants.genericError;
      }
      this.snackbarService.openSnackBar(this.responseMessage, GlobalConstants.error);
    })
  }

  edit() {
    var formData = this.busForm.value;
    var data = {
      id: this.dialogData.data.id,
      busName: formData.busName,
      day: formData.day,
      from: formData.from,
      to: formData.to,
      departTime:formData.departTime,
      arivalTime:formData.arivalTime,
      chargePerSeat:formData.chargePerSeat
    }
    this.busService.updateBus(data).subscribe((response: any) => {
      this.dialogRef.close();
      this.onEditBus.emit();
      this.responseMessage = response.message;
      this.snackbarService.openSnackBar(this.responseMessage, "success");
    }, (error: any) => {
      if (error.error?.message) {
        this.responseMessage = error.error?.message;
      }
      else {
        this.responseMessage = GlobalConstants.genericError;
      }
      this.snackbarService.openSnackBar(this.responseMessage, GlobalConstants.error);
    })
  }

}
