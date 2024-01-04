# Use named function
def make_multiplier(by):
    def multilpy(x):
        return x * by
    return multilpy

doubler = make_multiplier(2)

print(doubler(5)) #10
print(doubler(7)) #14

# Use lambda
def make_multiplier(by):
    return lambda x: x * by

doubler = make_multiplier(2)

print(doubler(5)) #10
print(doubler(7)) #14