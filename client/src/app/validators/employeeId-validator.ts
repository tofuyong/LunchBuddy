import { FormControl, ValidationErrors } from '@angular/forms';

export class EmployeeIdValidator {
    static NumbersOnly(control: FormControl): ValidationErrors | null {
        const employeeId: string = control.value;
        const numberPattern: RegExp = /^\d{5,8}$/;

        if (!numberPattern.test(employeeId)) {
            return { "NumbersOnly": true };
        }

        return null;
    }
}