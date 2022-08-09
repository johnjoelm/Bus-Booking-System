import { Component, EventEmitter, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { SnackbarService } from 'src/app/services/snackbar.service';
import { WalletService } from 'src/app/services/wallet.service';
import { GlobalConstants } from 'src/app/shared/global-constants';

@Component({
  selector: 'app-add-money',
  templateUrl: './add-money.component.html',
  styleUrls: ['./add-money.component.scss']
})
export class AddMoneyComponent implements OnInit {
  onAddMoney = new EventEmitter();
  addMoneyForm :any = FormGroup;
  responseMessage:any;

  constructor(@Inject(MAT_DIALOG_DATA) public dialogData:any,
  private formBuilder:FormBuilder,
  public dialogRef:MatDialogRef<AddMoneyComponent>,
  private snackbarService:SnackbarService,
  private walletService:WalletService) { }

  ngOnInit(): void {
    this.addMoneyForm = this.formBuilder.group({
      amount:[null,[Validators.required]]
    });
  }

  handleSubmit(){
    var formData = this.addMoneyForm.value;
    var data = {
      amount: formData.amount,
      userId: localStorage.getItem('id')
    }
    this.walletService.addMoney(data).subscribe((response:any)=>{
      this.dialogRef.close();
      this.onAddMoney.emit();
      this.responseMessage = response.message;
      this.snackbarService.openSnackBar(this.responseMessage,"success");
    },(error:any)=>{
      this.dialogRef.close();
      if(error.error?.message){
        this.responseMessage = error.error?.message;
      }
      else{
        this.responseMessage = GlobalConstants.genericError;
      }
      this.snackbarService.openSnackBar(this.responseMessage,GlobalConstants.error);
    })
  }

}
