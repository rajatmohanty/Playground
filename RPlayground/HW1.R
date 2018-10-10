# Read Data

library('foreign')
hie <- read.dta('rand.dta')

# 6.2 - 1

ins_0 = subset(hie, any_ins=='0')
ins_1 = subset(hie, any_ins=='1')
female_0_mean = mean(ins_0$female, na.rm=T)
female_1_mean = mean(ins_1$female, na.rm=T)
female_0_sd = sd(ins_0$female, na.rm=T)
female_1_sd = sd(ins_1$female, na.rm=T)
female_0_count = length(ins_0$female)
female_1_count = length(ins_1$female)

mystat <- function(x) {
   c(mean(x, na.rm=T), sd(x), length(x))
}

var = c('female', 'blackhisp', 'educper', 'ghindx', 'cholest')
aggregate(hie[var], list(hie$any_ins), mystat)

# 6.2 - 2

with(hie, t.test(female ~ any_ins, var.equal = TRUE))
with(hie, t.test(blackhisp ~ any_ins, var.equal = TRUE))
with(hie, t.test(educper ~ any_ins, var.equal = TRUE))
with(hie, t.test(ghindx ~ any_ins, var.equal = TRUE))
with(hie, t.test(cholest ~ any_ins, var.equal = TRUE))

# 6.2 - 3

var_2 = c('ghindxx', 'cholestx')
aggregate(hie[var_2], list(hie$any_ins), mystat)

with(hie, t.test(ghindxx ~ any_ins, var.equal = TRUE))
with(hie, t.test(cholestx ~ any_ins, var.equal = TRUE))
