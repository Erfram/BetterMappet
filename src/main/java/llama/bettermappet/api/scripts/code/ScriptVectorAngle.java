package llama.bettermappet.api.scripts.code;

import llama.bettermappet.api.scripts.user.IScriptVectorAngle;

import javax.vecmath.Vector4d;

public class ScriptVectorAngle implements IScriptVectorAngle {
    public double angle;
    public double x;
    public double y;
    public double z;

    public ScriptVectorAngle(double angle, double x, double y, double z) {
        this.angle = angle;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public ScriptVectorAngle(Vector4d vector) {
        this.angle = vector.w;
        this.x = vector.x;
        this.y = vector.y;
        this.z = vector.z;
    }

    @Override
    public String toString() {
        return "ScriptVectorAngle(" + this.angle + ", " + this.x + ", " + this.y + ", " + this.z + ")";
    }

    @Override
    public String toArrayString() {
        return "["+ this.angle + "," + this.x + ", " + this.y + ", " + this.z + "]";
    }

    @Override
    public IScriptVectorAngle add(ScriptVectorAngle other) {
        return new ScriptVectorAngle(this.angle + other.angle, this.x + other.x, this.y + other.y, this.z + other.z);
    }

    @Override
    public IScriptVectorAngle subtract(ScriptVectorAngle other) {
        return new ScriptVectorAngle(this.angle - other.angle, this.x - other.x, this.y - other.y, this.z - other.z);
    }

    @Override
    public IScriptVectorAngle multiply(double scalar) {
        return new ScriptVectorAngle(this.angle * scalar, this.x * scalar, this.y * scalar, this.z * scalar);
    }

    @Override
    public IScriptVectorAngle multiply(ScriptVectorAngle vector) {
        return new ScriptVectorAngle(this.angle * this.angle, this.x * vector.x, this.y * vector.y, this.z * vector.z);
    }

    @Override
    public double length() {
        return Math.sqrt(this.angle * this.angle + this.x * this.x + this.y * this.y + this.z * this.z);
    }

    @Override
    public IScriptVectorAngle normalize() {
        double length = this.length();
        return new ScriptVectorAngle(this.angle / length, this.x / length, this.y / length, this.z / length);
    }

    @Override
    public double dotProduct(ScriptVectorAngle vector) {
        return this.angle * this.angle + this.x * vector.x + this.y * vector.y + this.z * vector.z;
    }

    @Override
    public IScriptVectorAngle crossProduct(ScriptVectorAngle vector) {
        return new ScriptVectorAngle(
                -this.x * vector.y + this.y * vector.x + this.x * vector.z - this.z * vector.x,
                this.y * vector.z - this.z * vector.y - this.y * vector.angle + this.angle * vector.y,
                -this.x * vector.z + this.z * vector.x + this.x * vector.angle - this.angle * vector.x,
                this.x * vector.y - this.y * vector.x - this.x * vector.angle + this.angle * vector.x
        );
    }

    @Override
    public IScriptVectorAngle divide(ScriptVectorAngle vector) {
        return new ScriptVectorAngle(this.angle / vector.angle, this.x / vector.x, this.y / vector.y, this.z / vector.z);
    }

    @Override
    public IScriptVectorAngle copy() {
        return new ScriptVectorAngle(this.angle, this.x, this.y, this.z);
    }

    @Override
    public boolean equals(ScriptVectorAngle vector) {
        return (this.angle == vector.angle) && (this.x == vector.x) && (this.y == vector.y) && (this.z == vector.z);
    }

    @Override
    public boolean equals(double angle, double x, double y, double z) {
        return (this.x == angle) && (this.x == x) && (this.y == y) && (this.z == z);
    }
}
