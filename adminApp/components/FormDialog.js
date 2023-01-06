import * as React from 'react';
import Button from '@mui/material/Button';
import TextField from '@mui/material/TextField';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogTitle from '@mui/material/DialogTitle';


FormDialog.defaultProps = {
  title: "Submit",
  outlinedVar: "outlined",
  submit_button: "Submit",

}

export default function FormDialog({ fields, getValues, name, outlinedVar, title, submit_button, cancel_button , params, to_open}) {

  const [open, setOpen] = React.useState(to_open);

  const handleClickOpen = () => {
    setOpen(true);
    //Initialize the text fields values
    fields.map((f) => localStorage.setItem(f,""))
  };

  const handleClose = () => {
    let ans = [];
    if(cancel_button)
    {
      fields.map((f) => ans.push(localStorage.getItem(f)))
      ans.push(0);
    }
    getValues(ans);
    setOpen(false);
  };
  const handleSumbit = event => {
    console.log(`params = ${params}`);
    let ans = [];
    fields.map((f) => ans.push(localStorage.getItem(f)))
    if (params !== undefined)
    {
      params.map((p)=>ans.push(p));
    }
    if(cancel_button)
    {
      ans.push(1);
    }
    getValues(ans);
    setOpen(false);

  };
  const handleInputChange = event => {
    const name = event.target.name
    const value = event.target.value;
    console.log(value);
    localStorage.setItem(name, value);
  };

  return (
    <div>
      
      <Button variant={outlinedVar} onClick={handleClickOpen}>
        {name}
      </Button>
      <Dialog open={open} onClose={handleClose}>
        <DialogTitle>{title}</DialogTitle>
        <DialogContent>

          {/* (store_id, quantity,name, price, category, key_words) */}
          {fields.map((field) => (
            <TextField
              autoFocus
              margin="dense"
              id="name"
              name={field}
              label={field}
              type="storeid"
              fullWidth
              variant="standard"
              onChange={handleInputChange}
            />
          ))}





        </DialogContent>

        <DialogActions>
          <Button onClick={handleClose}>{cancel_button ? cancel_button : "Cancel"}</Button>
          <Button onClick={handleSumbit}>{submit_button}</Button>
        </DialogActions>
      </Dialog>
    </div>
  );
}
// }